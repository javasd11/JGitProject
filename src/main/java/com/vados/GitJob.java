package com.vados;

import org.eclipse.jgit.api.Git;

import java.util.List;
import java.util.Set;

public interface GitJob {
    public Git initRepository();

    public Git cloneRepository();

    public Git getRepository();

    public boolean addJob(String pattern);

    public boolean addJob(List<String> filesList);

    public boolean commitJob(String message);

    public boolean removeFileFromRepo(List<String> fileString);

    public Set<String> getStatus(Git git);

    public void createBrunch(String name);

}