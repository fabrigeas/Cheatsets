#~ alias cancel-commits-n='git reset --hard HEAD~'
#~ alias cancel-commits-n='git reset --soft HEAD~'
#~ alias delete-commit='git reset --hard HEAD~1 '
#~ git reset HEAD~3 //cancel last 3 commits but keep three
# PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h \[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]\n$'
# PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h:\[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]/'
#PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h \[\033[35m\]$MSYSTEM \[\033[33m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]\n$'
#PS1='\[\e]0;\w\a\]\n\[\e[32m\]\u@\h \[\e[35m\]$MSYSTEM\[\e[0m\] \[\e[33m\]\w\[\e[0m\]\n\$ '
alias ?='--help'
alias a='git add'
alias add='git add'
alias add-all='git add --all'
alias allias='code /etc/bash.bashrc &'
alias allias='geany /etc/bash.bashrc &'
alias amend='git commit --amend -am --no-edit'
alias amend='git commit --amend --no-edit'
alias ammend='git commit --amend -m '
alias b='git branch'
alias c='git commit -am'
alias cancel='reset;git reset HEAD --hard&&git clean -f'
alias cancel-commits-n='git reset HEAD~'
alias change-origin='echo "git push origin -u new-name"'
alias combine-file='echo "git checkout --patch branch file" '
alias comit='git commit -am'
alias commit='git commit -am'
alias d='cd ~/Desktop'
alias delete-commit='git reset HEAD~1 '
alias delete-remote-branch='git push origin --delete'
alias dhcpeth='netsh interface ip set address name=eth source=dhcp'
alias dhcpwlan='netsh interface ip set address name=wlan source=dhcp'
alias each='echo " $.each(object, function( name, value ) {});" '
alias egrep='egrep --color=auto'
alias enc='gpg -e -r'
alias fnd='ls *.txt | egrep -i '
alias gitk='gitk --all &'
alias home='cd ~'
alias ifconfigEth='netsh interface ip set address name=eth0 static'
alias ifconfigWlan='netsh interface ip set address name=wlan0 static'
alias l='reset;ls -al'
alias log='git log'
alias log='git log --oneline --decorate'
alias mysql='winpty mysql -u root&&use ibitest'
alias notes='cat ~/Git/notes/*.c *.txt'
alias pdf-to-txt='for i in $(ls *.pdf); do pdftotext $i; done && sed -i 's/[^[:print:]]//' *.txt'
alias pingLocal='ping 192.168.1.1'
alias pingRemote='ping 192.168.2.1'
alias pull='git pull'
alias push='git push origin'
alias r='reset'
alias reload='source /etc/bash.bashrc&&reset'
alias reload='source ~/bash.bashrc&&reset'
alias remote='git remote -v'
alias remove-whitespace-from-filenames='find . -name "*.pdf" -print | while read file; do    mv "${file}" "${file// /_}"; done'
alias rename-branch='echo -e "branch -m new-name || branch -m old-name new-name\ngit push origin :old-name new-name\n"'
alias rf='rm -rf'
alias s='reset;git status'
alias save='git commit -am "save" &&git tag -d save && git tag save'
alias save='git commit -am "saving: $(date)"'
alias show='reset;git show'
alias t='git tag'
alias thesis='start "C:\Users\fabrigeas\Documents\Studium\Thesis"'
alias to='git checkout'
alias undo='git revert HEAD '
alias unix='cat ~/Git/notes/unix.c'
alias unstage='git restore --staged'
alias update-all='git add --all && &git commit -am '
PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h \[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]\n$'
PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h:\[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]/'
PS1='\[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]/'
