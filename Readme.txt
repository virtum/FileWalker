Create web application which shows in real-time files and directories of some server-side directory (preferred either temp or home directory) including real-time changes.
Server-side should-t keep directories in memory - need to iterate through root folder and send them to the web application
Server-side need to observe folder / files changes to send those changes as well.

Key language learning points: iterables (to convert tree to flat iterable), reactivity (to convert structure changes to stream items and send through websockets)

User tools: github (to integrate sources with other tools) , travis CI, codecov.io (test coverage), codacy, docker
Key points

    Application should use Spring and Websockets.
    The directory tree should be presented as iterable.
    Application should be fully covered with unit and integration tests.
    Whenever a new file or directory is added web page contents also should be updated (make use of ReactiveX to handle changes)

 
Pre work

In challende1 crucial part of code is to avoid loading tree structure into memory. To understand this, start to train example with infinite iteration which force you to avoid loading "everything" into memory
Exqample: Let's consider cyclic meetings in every Thoursday and Friday. We know only starting date (e.g. 2016/IX/19) and we would like to go enumerate calendar days
Create fully tested class called Calendar which is Iterable<LocalData> and prove that

    You can initialize Calendar with 2016/IX/19 as the initial date, and you can obtain 2016/IX/20, 2016/IX/23, 2016/IX/26, 2016/IX/30 as initial To keep clean code, instead of 'manually' checking every returned value use collections assert available in either assertj or hamcrest.
    Calendar can produce independent iterators - using the first ne doesn't have an impact on the second one iteration

 
Check points:

    Step1: Create testable algo to convert tree-based structure to iterable collection (do not keep items in memory).
    Step2: Reuse algo created in Step1 to convert tree of files to reactive stream.
    Step3: Create testable strategy for watching changes in visited tree structure. Include observed changes in the reactive stream.
    Step4: Send stream results to reactive observer so that later we can send it to websocket client. Include integration tests,
    Step5: Prepare integration tests for spring client to show off how testable is your solution
    Step6: Pack it to docker and demonstrate

Verification:

    Download application from Docker Hub
    Open application in two browsers
    check if both provides list of files located in some server-side folder
    invoke http://(docker-instance-address)/addFile?name=1/2/3.txt
    check if both browsers are updated with just created file named 3.txt and located in 1/2 folder