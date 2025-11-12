# Task 2 – Restaurant Management System (CLI, Java)

Course: Desktop-Based Programming 
Instructor: Online Tutor (Tuton)

# 🎯 Learning Objectives

- Implement class, object, attributes, methods, and the concept of encapsulation.
- Use arrays to manage data (menu list and order cart).
- Apply decision structures: if, if-else, else-if, switch.
- Apply looping structures: for, enhanced for, while, do-while.
- Perform subtotal → discount/promo → tax (10%) → service charge calculations.

# 🧩 Task Description

Build a console-based restaurant ordering application in Java with the following specifications:

# 1️⃣ Project Structure (3 files)

- Menu.java → represents a single menu item with name, price, category (MAKANAN / MINUMAN) and helper methods isMakanan() / isMinuman().
- OrderLine.java → represents an order line containing a Menu object, qty, gratisBogo (free items for BOGO), and total calculation methods.
- Main.java → main application logic, including Admin and Customer interfaces, input handling, validation, computation, and invoice printing.

# 2️⃣ Required Features
Customer Mode
- Display all menu items grouped by category (Food / Drink).
- Allow user to type menu names repeatedly until typing "selesai".
- Invalid menu names trigger an error message and re-entry.
- Duplicate menu items are merged into a single cart entry.
- Apply BOGO (Buy-One-Get-One) promotion for drinks if subtotal exceeds Rp 50,000.
- Apply 10% discount for totals above Rp 100,000.
- Apply 10% tax and Rp 20,000 service charge.
- Print a detailed receipt/struk with per-item breakdown, subtotal, discounts, tax, and notes about active promos.

Admin Mode
- Display all menus (with numbering).
- Add multiple new menus in a loop until typing "selesai".
- Validate category input (MAKANAN / MINUMAN) and confirm before saving.
- Update the price of a menu item by selecting its number and confirming "Ya".
- Delete menu items by selecting their number and confirming "Ya".
- Return to the Admin Menu after each operation.

# 🗂️ Project Folder Structure
.
├── Menu.java        // menu item class
├── OrderLine.java   // order line class with total and BOGO logic
└── Main.java        // main logic, menus, admin, calculations

# ⚙️ How to Run
Compile and run the program from terminal:

javac Main.java Menu.java OrderLine.java
java Main

# 👨‍💻 Quick Demo Steps
1. Run the program → choose 1) Customer (Pemesanan).
2. The app displays food and drink categories.
3. Type a menu name (e.g., Nasi Goreng, Es Teh) → enter quantity → repeat until typing selesai.
4. The system calculates:
 - BOGO (Buy-One-Get-One) for drinks if applicable
 - 10% discount (if applicable)
 - 10% tax
 - Rp 20,000 service charge
   → then prints a formatted receipt (struk).
5. Return to 2) Admin Mode to add, edit, or delete menu items.

# 🧪 Recommended Test Scenarios
1. Order only food, total < Rp50,000 → no promos.
2. Order drinks total > Rp50,000 → check BOGO (free items appear on receipt).
3. Mixed order total > Rp100,000 → verify 10% discount, tax, and service charge.
4. In Admin Mode, test:
 - Add new menu item (confirm “Ya”).
 - Change the price of one item.
 - Delete one item → display updates correctly.

# 📝 Notes for Submission
1. Prepare a short demo video (≤ 15 minutes) explaining:
 - Customer & Admin workflows,
 - Calculation logic for BOGO and discounts,
 - Receipt sample output.
2. You may add small improvements (e.g., confirmation before exit) but don’t alter the main promo or tax rules.
