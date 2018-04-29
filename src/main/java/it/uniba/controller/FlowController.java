package it.uniba.controller;

import it.uniba.parsing.ZipParser;

public class FlowController
{
    private String currWorkspace;
    private ZipParser fileParser;
    
    public String getCurrWorkspace()
    {
        return currWorkspace;
    }
    
    public ZipParser getFileParser()
    {
        return fileParser;
    }
    
    public void setCurrWorkspace(String newWorkspace)
    {
        currWorkspace = newWorkspace;
    }
    
    public void setFileParser(ZipParser fileParser)
    {
        this.fileParser = fileParser;
    }
    
    public FlowController(String newWorkspace, Boolean quitStatus, ZipParser fileParser)
    {
        this.currWorkspace = newWorkspace;
        this.fileParser = fileParser;
    }
}
