const customers = new Map();
const merchants = new Map();

class Customer {
    constructor(id, balance) {
        this.id = id;
        this.balance = balance;
    }
}

class Merchant {
    constructor(id) {
        this.id = id;
        this.balance = 0;
        this.active = true;
    }
}

function run() {
    const lines = document.getElementById("input").value.trim().split("\n");
    let out = [];

    for (let line of lines) {
        const parts = line.trim().split(" ");
        const cmd = parts[0];

        if (cmd === "CREATE_CUSTOMER") {
            const [_, id, bal] = parts;
            if (!customers.has(id)) {
                customers.set(id, new Customer(id, Number(bal)));
            }

        } else if (cmd === "CREATE_MERCHANT") {
            const id = parts[1];
            if (!merchants.has(id)) {
                merchants.set(id, new Merchant(id));
            }

        } else if (cmd === "DEACTIVATE_MERCHANT") {
            const m = merchants.get(parts[1]);
            if (m) m.active = false;

        } else if (cmd === "PAY") {
            const [_, cId, mId, amt] = parts;
            const c = customers.get(cId);
            const m = merchants.get(mId);
            const amount = Number(amt);

            if (!c || !m || !m.active || c.balance < amount) {
                out.push("FAILURE");
            } else {
                c.balance -= amount;
                m.balance += amount;
                out.push("SUCCESS");
            }

        } else if (cmd === "REFUND") {
            const [_, mId, cId, amt] = parts;
            const c = customers.get(cId);
            const m = merchants.get(mId);
            const amount = Number(amt);

            if (!c || !m || m.balance < amount) {
                out.push("FAILURE");
            } else {
                m.balance -= amount;
                c.balance += amount;
                out.push("SUCCESS");
            }

        } else if (cmd === "TOP_MERCHANTS") {
            const k = Number(parts[1]);

            const top = Array.from(merchants.values())
                .sort((a, b) => {
                    if (b.balance !== a.balance) {
                        return b.balance - a.balance;
                    }
                    return a.id.localeCompare(b.id);
                })
                .slice(0, k)
                .map(m => m.id);

            out.push(top.join(" "));
        }
    }

    document.getElementById("output").innerText = out.join("\n");
}
