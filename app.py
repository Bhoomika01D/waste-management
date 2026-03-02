from flask import Flask, render_template, request, redirect, url_for, flash

app = Flask(__name__)
app.secret_key = 'eco_secret_key'  # Required for flash messages

class WasteManager:
    def __init__(self):
        self.inventory = {}

    def add_waste(self, waste_type, weight):
        waste_type = waste_type.strip().title()
        if waste_type in self.inventory:
            self.inventory[waste_type] += weight
        else:
            self.inventory[waste_type] = weight
        return f"Successfully added {weight}kg of {waste_type}."

    def get_inventory(self):
        return self.inventory

    def schedule_pickup(self):
        if not self.inventory:
            return False, "Inventory is empty. Nothing to pick up."
        self.inventory.clear()
        return True, "Pickup scheduled successfully! Inventory cleared."

# Initialize the manager
manager = WasteManager()

@app.route('/')
def index():
    return render_template('index.html', inventory=manager.get_inventory())

@app.route('/add', methods=['POST'])
def add_waste():
    waste_type = request.form.get('waste_type')
    try:
        weight = float(request.form.get('weight'))
        if weight <= 0:
            flash("Weight must be a positive number.", "error")
        else:
            msg = manager.add_waste(waste_type, weight)
            flash(msg, "success")
    except ValueError:
        flash("Invalid input. Please enter a numeric weight.", "error")
    
    return redirect(url_for('index'))

@app.route('/pickup', methods=['POST'])
def pickup():
    success, msg = manager.schedule_pickup()
    flash(msg, "success" if success else "error")
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True, port=5000)