package com.railway;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.railway.user.User;

public class Railway {

	private static final String W = "W";
	private static final String R = "R";
	private static final String C = "C";
	private static final int TOTAL_CONFIRMED_SEAT = 5;
	private static final int TOTAL_RAC_SEAT = 5;

	private List<User> confirmedList = new ArrayList<>(TOTAL_CONFIRMED_SEAT);
	private List<User> racList = new ArrayList<>(TOTAL_RAC_SEAT);
	private List<User> waitingList = new ArrayList<>();

	public void reserverSeat(Scanner sc) {
		System.out.println("Enter your name");
		String name = sc.next();
		System.out.println("Enter your email id");
		String email = sc.next();
		User user = new User(name, email);
		if (canConfirmed()) {
			confirmSeat(user);
			System.out.println("Your seat is confirmed.");
		} else if (canRAC()) {
			int racSeatCount = racSeat(user);
			System.out.println("Your seat is under RAC with Number: " + racSeatCount);
		} else {
			int waitingSeatCount = waitingSeat(user);
			System.out.println("Your seat is under waiting list with Number: " + waitingSeatCount);
		}
	}

	private int waitingSeat(User user) {
		waitingList.add(user);
		return waitingList.size();
	}

	private int racSeat(User user) {
		racList.add(user);
		return racList.size();
	}

	private boolean canRAC() {
		return racList.size() < TOTAL_RAC_SEAT;
	}

	private void confirmSeat(User user) {
		confirmedList.add(user);
	}

	private boolean canConfirmed() {
		return confirmedList.size() < TOTAL_CONFIRMED_SEAT;
	}

	public void cancelSeat(Scanner sc) {
		System.out.println("Enter your email id");
		String email = sc.next();
		if (isAlreadyConfirmed(email)) {
			cancelConfirmedSeat(email);
			moveUserFromRACToConfirmed();
			moveUserFromWaitingToRAC();
			System.out.println("Your confirmed reservation got canceled successfully.");
		} else if (isAlreadyRAC(email)) {
			cancelRACSeat(email);
			moveUserFromWaitingToRAC();
			System.out.println("Your RAC reservation got canceled successfully.");
		} else if (isAlreadyWaiting(email)) {
			cancelWaitingSeat(email);
		} else {
			System.out.println("Oops!, you haven't reservered your seat yet.");
		}
	}

	private void cancelWaitingSeat(String email) {
		User user = waitingList.stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
		waitingList.remove(user);
	}

	private void cancelRACSeat(String email) {
		User user = racList.stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
		racList.remove(user);
	}

	private void moveUserFromWaitingToRAC() {
		if (waitingList.size() > 0) {
			User user = waitingList.get(0);
			waitingList.remove(0);
			racList.add(user);
		}
	}

	private void moveUserFromRACToConfirmed() {
		if (racList.size() > 0) {
			User user = racList.get(0);
			racList.remove(0);
			confirmedList.add(user);
		}
	}

	private void cancelConfirmedSeat(String email) {
		User user = confirmedList.stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
		confirmedList.remove(user);
	}

	private boolean isAlreadyWaiting(String email) {
		for (User user : waitingList) {
			if (email.equals(user.getEmail())) {
				return true;
			}
		}
		return false;
	}

	private boolean isAlreadyRAC(String email) {
		for (User user : racList) {
			if (email.equals(user.getEmail())) {
				return true;
			}
		}
		return false;
	}

	private boolean isAlreadyConfirmed(String email) {
		for (User user : confirmedList) {
			if (email.equals(user.getEmail())) {
				return true;
			}
		}
		return false;
	}

	public void detailInfo() {
		System.out.println("******************");
		System.out.println("No seat confirmed: " + confirmedList.size());
		System.out.println("No seat under RAC: " + racList.size());
		System.out.println("No seat under Waiting list: " + waitingList.size());
		System.out.println("Total number of seat available: " + (TOTAL_CONFIRMED_SEAT - confirmedList.size()));
		System.out.println("******************");
	}

	public void detailInfoOnUserChoice(Scanner sc) {
		System.out.println("Enter 'C', 'R' or 'W' for specific details");
		String choice = sc.next();
		detailInfoOnUserChoice(choice);
	}

	private void detailInfoOnUserChoice(String choice) {
		System.out.println("******************");
		if (C.equals(choice)) {
			System.out.println("Confirmed seats details");
			printUsers(confirmedList);
		} else if (R.equals(choice)) {
			System.out.println("RAC seats details");
			printUsers(racList);
		} else if (W.equals(choice)) {
			System.out.println("Waiting list seats details");
			printUsers(waitingList);
		}
		System.out.println("******************");
	}

	private void printUsers(List<User> userList) {
		for (User user : userList) {
			System.out.println(user.toString());
		}
	}

	public void printAll() {
		System.out.println("******************");
		printUsers(confirmedList);
		printUsers(racList);
		printUsers(waitingList);
		System.out.println("******************");
		detailInfo();
	}
}
