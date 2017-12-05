package com.vados;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UserSettings {

    private String localRepoPath;
    private String userName;
    private String password;
    private String repoURL;

    public String getRepoURL() {
        return repoURL;
    }

    public void setRepoURL(String repoURL) {
        this.repoURL = repoURL;
    }

    public void setLocalRepoPath(String localRepoPath) {
        this.localRepoPath = localRepoPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getRepositoryPath() {
        File file = new File(localRepoPath);
        return file;
    }
}
