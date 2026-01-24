let seats = [];
let n = 0;

function run() {
    const lines = document.getElementById("input").value.trim().split("\n");
    let output = [];

    for (let line of lines) {
        const parts = line.trim().split(" ");
        const cmd = parts[0];

        if (cmd === "INIT") {
            n = Number(parts[1]);
            seats = Array(n).fill("AVAILABLE");

        } else if (cmd === "BOOK") {
            const seatId = Number(parts[1]) - 1;
            if (isValid(seatId) && seats[seatId] === "AVAILABLE") {
                seats[seatId] = "BOOKED";
                output.push("SUCCESS");
            } else {
                output.push("FAILURE");
            }

        } else if (cmd === "CANCEL") {
            const seatId = Number(parts[1]) - 1;
            if (isValid(seatId) && seats[seatId] === "BOOKED") {
                seats[seatId] = "AVAILABLE";
                output.push("SUCCESS");
            } else {
                output.push("FAILURE");
            }

        } else if (cmd === "BOOK_K") {
            let k = Number(parts[1]);
            let booked = [];

            for (let i = 0; i < n && k > 0; i++) {
                if (seats[i] === "AVAILABLE") {
                    seats[i] = "BOOKED";
                    booked.push(i + 1);
                    k--;
                }
            }

            if (k === 0) {
                output.push(booked.join(" "));
            } else {
                for (let seat of booked) {
                    seats[seat - 1] = "AVAILABLE";
                }
                output.push("FAILURE");
            }

        } else if (cmd === "STATUS") {
            output.push(seats.join(" "));
        }
    }

    document.getElementById("output").innerText = output.join("\n");
}

function isValid(seatId) {
    return seatId >= 0 && seatId < n;
}
