### Latex
[Tutorial](https://www.latex-tutorial.com/tutorials/)
[Tutorial](https://www.latex-tutorial.com/tutorials/bibtex/)

### Download 

[Windows](https://miktex.org/download/ctan/systems/win32/miktex/setup/basic-miktex-2.9.6520-x64.exe)

### Basic Layout
        \documentclass{article}
        \begin{document}
          Hello World!
        \end{document} 
        
        --------------------------------
        % this is a comment
        
        \begin{document}
          \begin{environment1}
            \begin{environment2}
            \end{environment2}
          \end{environment1}
        \end{document}
        
        %Invalid:
        
        \begin{document}
          \begin{environment1}
            \begin{environment2}
          \end{environment1}https://www.latex-tutorial.com/tutorials/bibtex/
            \end{environment2}
        \end{document}
        
        % Invalid:
        
        \begin{document}
          \begin{environment1}
        \end{document}
          \end{environment1}
        
        % Also invalid:
        
        \begin{environment}
          \begin{document}
          \end{document}
        \end{environment}

#### First Document
    \documentclass{article}
    
    \title{My first document}
    \date{2013-09-01}
    \author{Fabrice Feugang}
    
    \begin{document}
      \maketitle
      \newpage
    
      Hello World!
    \end{document}