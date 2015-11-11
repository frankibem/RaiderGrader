# RaiderGrader
A blackboard inspired application that allows teachers and students to track their work.

## Testing
- Download a copy of the project
- Open the project in Android Studio
- Launch with emulator or suitable android device (preferred, must be API 21 and above)

#### Logging in
- You can log in with these details:
- Student: Email-- student@rgs.com  Password-- Texas*6
- Teacher: Email-- teacher@rgs.com  Password-- Texas*6

OR

- If you prefer, you can create a new account for testing (see "Creating an account" below).
- If successful, you will be taken to the class list page which shows all classes that the current user has enrolled in/created.

#### Creating an account: (User)
- Hit the registration bottom at the top of the login page
- Enter an email address and password
- Password rules: At least 6 characters in length, at least 1 uppercase letter, 1 lowercase letter, a digit and a punctuation.
- If registration is successful, you will be taken back to the login page.

####The Following Assume You Are Logged In As A Student

#### Enrolling in a class: (Student)
- To enroll in a class click the orange add button on the bottom right.
- To enroll, you need the ID (Software engineering: 1, Theory of Automata: 2, Computer Architecture: 3).
- If the correct ID is entered you will be taken to the confirmation page where you can see detais about the class.
- To complete the enrollment, click the confirm button.
- If enrollment is successful, you will be taken back to the class list page which should show the new class.
- Note that if the student is already enrolled in a class, re-enrolling will fail.

#### Viewing Announcements: (Student)
- To view announcements click on the class you wish to view announcements for.
- Click on 'Announcements'.
- If there are any announcements for the class, a list will be displayed with the announcements as its content.

#### Viewing Announcement Details: (Student)
- To view the details of an announcement first navigate to viewing the list of announcmeents as described above.
- Click on an individual announcement.
- The details of the announcement, including title, date, and description will be displayed.

#### Viewing Work Items: (Student)
- To view work items click on the class you wish to view work items for.
- Click on 'Work Items'.
- If there are any work items for the class, a lsit will be displayed with the work items as its content.

#### Viewing Work Item Details: (Student)
- To view the details of a work item first navigate to viewing the list of work items as described above.
- Click on an individual work item.
- The details of the work item, including title, due date, points possible, and description will be displayed.

####The Following Assume You are Logged In As A Teacher

#### Viewing Announcements: (Teacher)
- To view announcements click on the class you wish to view announcements for.
- Click on 'Announcements'.
- If there are any announcements for the class, a list will be displayed with the announcements as its content.

#### Viewing Announcement Details: (Teacher)
- To view the details of an announcement first navigate to viewing the list of announcmeents as described above.
- Click on an individual announcement.
- The details of the announcement, including title, date, and description will be displayed.

#### Viewing Work Items: (Teacher)
- To view work items click on the class you wish to view work items for.
- Click on 'Work Items'.
- If there are any work items for the class, a lsit will be displayed with the work items as its content.

#### Viewing Work Item Details: (Teacher)
- To view the details of a work item first navigate to viewing the list of work items as described above.
- Click on an individual work item.
- The details of the work item, including title, due date, points possible, and description will be displayed.

#### Creating Classes:
- To create a class, at the class list page click on the orange add button at the bottom right.
- Proceed to fill in class information. 
- The first section deals with the title of the course.
- The second section deals with grading of work items.
- Title, Prefix, and Section should contain alphabet characters only.
- Course Number, should contain numeric characters only.
- Exam, Project, Quiz, Homework, and Other should contain numeric characters and the decimal only.
- Click on create after all information has been entered.
- If information was entered correctly you will be taken back to the class list screen with the new class added to the list.
- Note: Attempting to create multiple classes with the same values will result in a failure to create.

#### Creating Work Items:
- To create a work item, navigate to the work item list page within a class.
- Click on the orange add button at the bottom right.
- Proceed to fill in work item information.
- Title and Description should be Strings.
- Set Time and Set Date should contain time and date information respectively.
- Work Item Type will be one of: Exam, Quiz, Homework, Project, or Other.
- After information has been entered, click on 'Done'.
- If information was entered correctly you will be taken back to the work item list page and the new work item will be added to the list.
