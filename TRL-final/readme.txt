Currently there are two names in the database. I list them below in the format:
ID, name, Hold-status:
1, Dave, with hold
2, Sarah, without hold


The system currently has these book names hardcoded as part of the instantiation process
1, This book is boring, some guy
2, This book is boring, Some guy
3, Don't read this", Who cares
4, Who even reads books anymore, Bitter programmer
5, Books are outdated, Dr. McTechie
6, All knowledge in the world, God?
7, The shortest book in the world, Steve
8, Book #391, Stephen King
9, Please by this book, I need money, McBeggar
10, Theoretical physics for kids", Smarty Marty
11, The last book in the library, Librarian

Worthy of noting is that the ISBN are generated randomly as the controller instantiates, meaning, you will get different ISBNs for the same book each time you run the application. This should not affect the process as we look up the copy by the UPC code (1, 2, 3...etc)

Features:

Changes from previous versions and refactoring(s):

//1111111111