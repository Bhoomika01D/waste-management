import sys

class WasteManager:
    def __init__(self):
        self.inventory = {}

    def add_waste(self, waste_type, weight):
        if waste_type in self.inventory:
            self.inventory[waste_type] += weight
        else:
            self.inventory[waste_type] = weight
        print(f"Added {weight}kg of {waste_type}.")

    def view_inventory(self):
        print("\n--- Current Waste Inventory ---")
        if not self.inventory:
            print("Inventory is empty.")
        else:
            for waste_type, weight in self.inventory.items():
                print(f"{waste_type}: {weight}kg")
        print("-------------------------------\n")

    def schedule_pickup(self):
        if not self.inventory:
            print("Nothing to pick up.")
            return
        print("\nScheduling pickup for the following items:")
        self.view_inventory()
        self.inventory.clear()
        print("Pickup scheduled. Inventory cleared.")

def main():
    manager = WasteManager()
    while True:
        print("1. Add Waste")
        print("2. View Inventory")
        print("3. Schedule Pickup")
        print("4. Exit")
        choice = input("Enter choice: ")

        if choice == '1':
            w_type = input("Enter waste type (e.g., Plastic, Organic): ")
            try:
                weight = float(input("Enter weight in kg: "))
                manager.add_waste(w_type, weight)
            except ValueError:
                print("Invalid weight. Please enter a number.")
        elif choice == '2':
            manager.view_inventory()
        elif choice == '3':
            manager.schedule_pickup()
        elif choice == '4':
            print("Exiting...")
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()