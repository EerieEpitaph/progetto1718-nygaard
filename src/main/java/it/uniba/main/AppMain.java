package it.uniba.main;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.zip.ZipException;

import com.google.gson.JsonParseException;

//import it.uniba.controller.input.*;
import it.uniba.controller.input.CommandLine.UnmatchedArgumentException;
import it.uniba.parsing.ZipParser;
import it.uniba.controller.*;

public final class AppMain {
	 
	public static void main(final String[] args) {
		ZipParser fileParser = new ZipParser();
		
		Controller control = new Controller();

		try {
	 
			if (args.length != 0)
				control.controlExecuteCLI(args,fileParser);
			else
				control.showHelp();
		}  catch (NullPointerException e) {
			System.out.println("NullPointer encountered");
		} catch (ZipException e) {
			System.out.println("Unable to analyze. Damaged or wrong file");
		} catch (InvalidPathException e) {
			System.out.println("Illegal char used in path");
		} catch (IOException e) {
			System.out.println("Invalid file. Usage: -w \"path\\to\\file.zip\"");
		} catch (JsonParseException e) {
			System.out.println(e.toString());
		} catch (UnmatchedArgumentException e) {
			System.out.println("Invalid syntax. Refer to 'help' command");
		} catch (IllegalStateException e) {
			System.out.println("Invalid semantics. Refer to 'help' command");
		} catch (Exception e) {
			System.out.println("Unexpected exception encountered");
		}
	}
}
