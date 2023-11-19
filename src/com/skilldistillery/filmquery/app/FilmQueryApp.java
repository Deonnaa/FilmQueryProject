package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	Actor actor = new Actor();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		try {
			app.test();
			app.launch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void test() throws SQLException {
//		Film film = db.findFilmById(1);
//		Film film = db.findFilmById(1005); //null
//		System.out.println(film);

//		Actor actor = db.findActorById(1);
//		Actor actor = db.findActorById(201); //null
//		System.out.println(actor);
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);
		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		boolean keepRunning = true;
		while (keepRunning) {
			displayMenu();
			System.out.print("Enter your choice: ");
			int choice = input.nextInt();

			switch (choice) {
			case 1:
				lookUpFilmById(input);
				break;
			case 2:
				searchFilmByKeyword(input);
				break;
			case 3:
				System.out.println("Exiting application...");
				keepRunning = false;
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void displayMenu() {
		System.out.println("\nMenu:");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
	}

	private void lookUpFilmById(Scanner input) {
		System.out.print("Enter film ID: ");
		int id = input.nextInt();
		Film film = null;
		try {
			film = db.findFilmById(id);
			if (film != null) {
				displayFilmDetails(film);
			} else {
				System.out.println("No film found with ID: " + id);
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while retrieving film.");
			e.printStackTrace();
		}
	}

	private void searchFilmByKeyword(Scanner input) {
		System.out.print("Enter a search keyword: ");
		String keyword = input.next();

		List<Film> films;
		try {
			films = db.findFilmsByKeyword(keyword);
			if (films.isEmpty()) {
				System.out.println("No films found with the keyword '" + keyword + "'.");
			} else {
				System.out.println("Films found:");
				for (Film film : films) {
					displayFilmDetails(film);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error occurred while searching for films.");
			e.printStackTrace();
		}
	}

	private void displayFilmDetails(Film film) {
		System.out.println("Title: " + film.getTitle());
		System.out.println("Description: " + film.getDesc());
		System.out.println("Release Year: " + film.getReleaseYear());
		System.out.println("Rating: " + film.getRating());
		System.out.println("Language: " + film.getLanguage());
		System.out.println("Actors: ");
		for (Actor actor : film.getActors()) {
			System.out.println("\t" + actor.getFirstName() + " " + actor.getLastName());
		}
	}

}
