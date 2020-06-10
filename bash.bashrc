# To the extent possible under law, the author(s) have dedicated all 
# copyright and related and neighboring rights to this software to the 
# public domain worldwide. This software is distributed without any warranty. 
# You should have received a copy of the CC0 Public Domain Dedication along 
# with this software. 
# If not, see <http://creativecommons.org/publicdomain/zero/1.0/>. 


# Fixup git-bash in non login env
shopt -q login_shell || . /etc/profile.d/git-prompt.sh

#networking
alias dhcpwlan='netsh interface ip set address name=wlan source=dhcp'
alias dhcpeth='netsh interface ip set address name=eth source=dhcp'
alias pingLocal='ping 192.168.1.1'
# /etc/bash.bashrc: executed by bash(1) for interactive shells.

# System-wide bashrc file

# Check that we haven't already been sourced.
([[ -z ${CYG_SYS_BASHRC} ]] && CYG_SYS_BASHRC="1") || return

# If not running interactively, don't do anything
[[ "$-" != *i* ]] && return

# Set a default prompt of: user@host, MSYSTEM variable, and current_directory
#PS1='\[\e]0;\w\a\]\n\[\e[32m\]\u@\h \[\e[35m\]$MSYSTEM\[\e[0m\] \[\e[33m\]\w\[\e[0m\]\n\$ '

# Uncomment to use the terminal colours set in DIR_COLORS
# eval "$(dircolors -b /etc/DIR_COLORS)"

# Fixup git-bash in non login env
shopt -q login_shell || . /etc/profile.d/git-prompt.sh
alias ifconfigEth='netsh interface ip set address name=eth0 static'
alias ifconfigWlan='netsh interface ip set address name=wlan0 static'
alias pingRemote='ping 192.168.2.1'

#default
#PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h \[\033[35m\]$MSYSTEM \[\033[33m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]\n$'

PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h \[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]\n$'
PS1='\[\033]0;$MSYSTEM:${PWD//[^[:ascii:]]/?}\007\]\n\[\033[32m\]\u@\h:\[\033[35m\]\w\[\033[36m\]`__git_ps1`\[\033[0m\]/'

## GIT 

#alias update='git add --all && &git commit -am '
alias update-all='git add --all && &git commit -am '

alias amend='git commit --amend --no-edit'
alias ammend='git commit --amend -m '
alias save='git commit -am "save" &&git tag -d save && git tag save'
alias comit='git commit -am \"--\"'
alias commit='git commit -am'
#git reset
#~ git reset HEAD~3 //cancel last 3 commits but keep three
#~ alias cancel-commits-n='git reset --hard HEAD~' 
#~ alias cancel-commits-n='git reset --soft HEAD~' 
alias cancel-commits-n='git reset HEAD~' 


alias add='git add'
alias unstage='git restore --staged'
alias add-all='git add --all'

alias push='git push origin'
alias pull='git pull'

alias b='git branch'
alias a='git add'
alias c='git commit -am'
alias t='git tag'



# go with detached head 2 commits earlier, NB reate e branch here before dirtying
#~ git checkout HEAD~2


#~alias delete-commit='git reset --hard HEAD~1 '
alias delete-commit='git reset HEAD~1 '
alias undo='git revert HEAD '
alias cancel='reset;git reset HEAD --hard&&git clean -f'

alias show='reset;git show'
alias log='git log'
alias log='git log --oneline --decorate'
#~ git shortlog
#~ git log --graph --oneline --decorate

alias gitk='gitk --all &'


alias remote='git remote -v'

alias mysql='winpty mysql -u root&&use ibitest'

## Brancehes
alias delete-remote-branch='git push origin --delete'
alias rename-branch='echo -e "branch -m new-name || branch -m old-name new-name\ngit push origin :old-name new-name\n"'
alias change-origin='echo "git push origin -u new-name"'
alias to='git checkout'

#Frequently used functions
alias each='echo " $.each(object, function( name, value ) {});" '
alias allias='geany /etc/bash.bashrc &'

#GPG
#alias enc='gpg --encrypt --sign --armor -r'
alias enc='gpg -e -r'

## Destinations
alias d='cd ~/Desktop'
alias home='cd ~'

# misc
alias s='reset;git status'
alias ?='--help'
alias l='reset;ls -al'
alias r='reset'
alias reload='source /etc/bash.bashrc&&reset'
alias rf='rm -rf'
#run this command when a in a root containing ./Lib project ../Unity
#alias build='ant jar && rm -rf ../../UnityProject/Assets/Plugin/Android/* && cp bin/Plugin.jar ../UnityProject/Assets/Plugin/Android &&echo "export  succes"'
#alias build='rm -rf Unity/Assets/Plugin/Android/* && cp app/src/main/AndroidManifest.xml Unity/Assets/Plugin/Android/ && cp app/release/AndroidPlugin.jar Unity/Assets/Plugin/Android/ && echo "export  success"'


#shortcuts
alias fnd='ls *.txt | egrep -i '
alias pdf-to-txt='for i in $(ls *.pdf); do pdftotext $i; done && sed -i 's/[^[:print:]]//' *.txt'
alias remove-whitespace-from-filenames='find . -name "*.pdf" -print | while read file; do    mv "${file}" "${file// /_}"; done'
alias combine-file='echo "git checkout --patch branch file" '
alias thesis='start "C:\Users\fabrigeas\Documents\Studium\Thesis"'
alias notes='cat ~/Git/notes/*.c *.txt'
alias unix='cat ~/Git/notes/unix.c'

alias egrep='egrep --color=auto'
