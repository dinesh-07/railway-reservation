package com.railway;

import java.util.Scanner;

public class RailwayReservation {

	public static void main(String[] args) {
		Railway railway = new Railway();
		printAuthorInfo("Pallavi", "4JN21IS456", "A");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("******************");
			System.out.println("1. Reservation");
			System.out.println("2. Cancellation");
			System.out.println("3. Details of the three list, availability etc.");
			System.out.println("4. Settings - C, R and W tickets");
			System.out.println("0. Exit");
			System.out.println("******************");
			boolean canBreak = handleReservationOption(sc, railway);
			if (canBreak) {
				break;
			}
		}
	}

	private static boolean handleReservationOption(Scanner sc, Railway railway) {
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			railway.reserverSeat(sc);
			break;
		case 2:
			railway.cancelSeat(sc);
			break;
		case 3:
			railway.detailInfo();
			break;
		case 4:
			railway.detailInfoOnUserChoice(sc);
			break;
		case 0:
			railway.printAll();
			return true;
		default:
			System.out.println("Ooops!, wrong choice. Please select correct choice...");
		}
		return false;
	}

	private static void printAuthorInfo(String name, String usn, String section) {
		System.out.println("******************");
		System.out.println("Name: " + name);
		System.out.println("USN: " + usn);
		System.out.println("Section: " + section);
		System.out.println("******************");
	}
}
