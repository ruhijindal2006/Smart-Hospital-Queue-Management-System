import mysql.Connector

# Database connection
def connect_db():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        password="ruhijindal@12",
        database="hospital_db"
    )

# Add patient
def add_patient():
    name = input("Enter patient name: ")
    age = int(input("Enter age: "))
    disease = input("Enter disease: ")

    db = connect_db()
    cursor = db.cursor()

    query = "INSERT INTO patients (name, age, disease) VALUES (%s, %s, %s)"
    values = (name, age, disease)

    cursor.execute(query, values)
    db.commit()

    print("✅ Patient added successfully!")

# View patients
def view_patients():
    db = connect_db()
    cursor = db.cursor()

    cursor.execute("SELECT * FROM patients")
    records = cursor.fetchall()

    print("\n--- Patient Records ---")
    for row in records:
        print(f"ID: {row[0]}, Name: {row[1]}, Age: {row[2]}, Disease: {row[3]}")

# Search patient
def search_patient():
    pid = int(input("Enter patient ID: "))

    db = connect_db()
    cursor = db.cursor()

    cursor.execute("SELECT * FROM patients WHERE id=%s", (pid,))
    record = cursor.fetchone()

    if record:
        print(f"Found: {record}")
    else:
        print("❌ Patient not found")

# Delete patient
def delete_patient():
    pid = int(input("Enter patient ID to delete: "))

    db = connect_db()
    cursor = db.cursor()

    cursor.execute("DELETE FROM patients WHERE id=%s", (pid,))
    db.commit()

    print("🗑️ Patient deleted successfully!")

# Menu
def menu():
    while True:
        print("\n--- Hospital Management System ---")
        print("1. Add Patient")
        print("2. View Patients")
        print("3. Search Patient")
        print("4. Delete Patient")
        print("5. Exit")

        choice = input("Enter choice: ")

        if choice == '1':
            add_patient()
        elif choice == '2':
            view_patients()
        elif choice == '3':
            search_patient()
        elif choice == '4':
            delete_patient()
        elif choice == '5':
            print("Exiting...")
            break
        else:
            print("Invalid choice!")

# Run program
menu()