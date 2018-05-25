package it.uniba.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.zip.ZipException;

import org.junit.jupiter.api.Test;

import it.uniba.controller.Controller;
import picocli.CommandLine.MissingParameterException;

public class ControllerTester
{
    Controller control;
    
    //Provo ad immettere un path Illegale
    @Test
    void invalidPathTester() throws ZipException, IOException
    {
        String[] args = {"-w", "§no.zip"};
        control = new Controller();
        assertThrows(IOException.class, () -> {control.controlExecuteCLI(args);});
    }
    
    //Provo ad immettere un path mezzo fatto
    @Test
    void goodPathTester() throws ZipException, IOException
    {
        String[] args = {"-w"};
        control = new Controller();
        assertThrows(MissingParameterException.class, () -> {control.controlExecuteCLI(args);});
    }
}
