@ECHO OFF

REM kill chrome
REM taskkill /IM programsName*

    call :trippe resultOfTripple=3
    echo %resultOfTripple%
    goto :door

    :trippe
        set /a %1=%2 *3
    exit /b
        


    :door
    pause
    exit