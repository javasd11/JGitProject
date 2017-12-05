package com.vados;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class GitJobImpl implements GitJob {

    private Git git;
    @Autowired

    UserSettings userSettings;

    @Override
    public Git initRepository() {
        try (Git git = Git.init().setDirectory(userSettings.getRepositoryPath()).call()) {
            this.git = git;
            return git;
        } catch (IllegalStateException | GitAPIException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Git cloneRepository() {
        try (Git result = Git.cloneRepository()
                .setURI(userSettings.getRepoURL())
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("username", "password"))
                .setDirectory(userSettings.getRepositoryPath())
                .call()) {
            return result;
        } catch (IllegalStateException | GitAPIException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Git getRepository() {


        //if (git == null) {
        try {
            git = Git.open(userSettings.getRepositoryPath());
        } catch (IOException e) {
            System.out.println("Repository does not exist");
            if (git == null) {
                this.initRepository();
            }
        }
        // }

        return git;
    }

    @Override
    public boolean addJob(String pattern) {
        try (Git git = Git.open(userSettings.getRepositoryPath())) {
            git.add().addFilepattern(pattern).call();
        } catch (IOException | GitAPIException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addJob(List<String> filesList) {
        try (Git git = Git.open(userSettings.getRepositoryPath())) {
            for (String fileItem : filesList) {
                git.add().addFilepattern(fileItem).call();
            }
            Status status = git.status().call();
            Set<String> set = status.getAdded();
            System.out.println(set.size());
        } catch (IOException | GitAPIException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean commitJob(String message) {
        try {
            Status status = getRepository().status().call();
            if (!status.getAdded().isEmpty() && !message.isEmpty()) {
                getRepository().commit().setMessage(message).call();
            }
        } catch (GitAPIException e) {
            System.err.println(e.getMessage());
            //TODO: Log4j
        }
        return false;
    }


    @Override
    public void createBrunch(String name) {
        Git git = getRepository();
        try {
            git.branchCreate().setName(name).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeFileFromRepo(List<String> fileString) {
        return false;
    }

    @Override
    public Set<String> getStatus(Git git) {
        return null;
    }


}
