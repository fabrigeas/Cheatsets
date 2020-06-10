# Tutorials
[edit commit](https://github.com/k88hudson/git-flight-rules#i-wrote-the-wrong-thing-in-a-commit-message)


SUMMARY

    - The most important commands
    - NB in this file, every text that comes after# is a comment. # this for example is a comment 
    - open git shell and navigate to your Repository( Folder/Project)

    git status  # to view the status of your repository 
    #if git status says: fatal: Not a git repository (or any of the parent directories): .git.... then run:
    git init 
    #Explanations of git status output
    #files/folders that are RED in color are not added to git yet,run:
    git add --all or git add file1.txt file2.txt folder/* 

    #files/folders that are GRENEN in color have been modified and need to be commited, run:
    git commit -am "a short description of the changes that you have made to these files"




Exercise

    - Create a repository( folder) called HelloRepository
    - initialize it (open git shell and cd HelloRepository/)
    - create a file names 'myFile.txt'
    - add it to git (git add myFile'
    - commit it with message "I have created an empty file called myFile.txt'
    - create another file called myFile2.txt
    - add myFile 2
    - commit with message "created myFile2.txt"
    - write a random text in myFile.txt
    - commit with a message (you dont need to add myFile again because you have already added it)
    - write something to myFile2.txt and commit it
    - compress helloRepository as zip and email it to me (fabrigeas@gmail.com)
-------------------------------------------------------------------------------------

    git log --oneline //to view commits with hashcodes on the side.
    returns:
    abcdef commit mesage
    asdasd commit message.

    git checkout abcdef // returns files to this state

    create file 
    commit:file added

REVERT ALL CHANGES TILL LAST COMMIT

    git reset HEAD --hard && git clean -f
    
    edit file "abc"
    commit: -am"abc added to file"
    
    edit file: "def"
    commit: -am "def added to file"


    git log --oneline
    aaaaaa file added
    bbbbbb abc added...
    cccccc def added....

    CHECKOUT FILE
    git checkout bbbbbb fileX //load fileX at commit bbbb
    # u can decide to keep this loaded version, by commiting
    or return to he head version git checkout HEAD fileX 

Unstage object

    git reset object
    remoes object from the staging area

Undo -checkout

    git checkout --object
    resets object to the state it waas before the last git commit

    git commit --amend 
    #to modify a file after commit.
    #(commit -m okay but, file was wrong, then edit the file after the commit
    #then commit --amend --no-edit)

    #to revert(cancel) last commit.
    git revert HEAD 


    git reset fileX
    # equivalent to git remove fileX
    
Time machine

    git reflog # list indexed commits
    git reset HEAD@{index} # back to index

move to 2 commits before

    git reset --hard HEAD~2


RENAME BRANCH (CURRENT)

    branch -m new-name. .
    git push origin :old-name new-name.
    git push origin -u new-name.
    
    git branch -m old_branch new_branch         # Rename branch locally    
    git push origin :old_branch                 # Delete the old branch    
    git push --set-upstream origin new_branch   # Push the new branch, set local branch to track the new remote


DELETE REMOTE BRANCH

    git push origin --delete <branchName>
    OR
    git push origin :<branchName>
    
    DELETE ALL ALREADY MERGED BRANCHES
    git branch --merged | grep -v "\*" | grep -v master | grep -v dev | xargs -n 1 git branch -d



git autocomplete

    A windows 
    1 download file git-flow-completion.bash (A)
    2 create file .bashrc (B)
    3 insert content source ~/git-flow-completion.bash in .bashrc
    4 open git bash(Terminal) 
    5 paste git-flow-completion.bash,.bashrc in ~/
    use tab to auto complete


CHANGING A REMOTE'S URL

    git remote set-url origin git@github.com:fabrigeas/Notes.git(the copied rss url from remote repo)


[ssh add key to bitbucket | push without login](https://confluence.atlassian.com/bitbucket/set-up-an-ssh-key-728138079.html)
	
    //Open Git Bash.
	
	//generate a rsa key if you dont have any key pair
	#ssh-keygen -t rsa -b 4096 -C "fabrigeas@gmail.com"
	
	//test that ssa-agent is runnning to be able to add my private key
	eval "$(ssh-agent -s)"
	
	//add private key to ssa-agent(most important step)
	ssh-add ~/.ssh/id_rsa
	
	//copy the rsa public key into clipboard
	clip < ~/.ssh/id_rsa.pub

	//in remote
	settings->SSH and GPG keys-> New SSH key or Add SSH key(provide title and paste content)

	clone repo using ssh
	//summary


shortcut to push

    rm *~
	git config --global user.name  fabrigeas
	git config --global user.email fabrigeas@gmail.com
	git init
	git add --all
	git commit -m "$(date +"%c")"
	git remote add origin https://github.com/fabrigeas/Notes.git
	git push -u origin master


remove file from commit

    git checkout HEAD^ myfile
    git add myfile
    git commit --amend --no-edit


Delete last commit

    $ git reset HEAD^ --hard
    $ git push --force-with-lease [remote] [branch]
    
    // if you haven't pushed the bad commit
    git reset --soft HEAD@{1}
    
    // if you had pushed
    git revert SHAofBadCommit
    
Delete an arbitrary commit

    $ git rebase --onto SHA1_OF_BAD_COMMIT^ SHA1_OF_BAD_COMMIT
    $ git push --force-with-lease [remote] [branch]