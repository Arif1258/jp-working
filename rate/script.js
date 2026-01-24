let K = 0; 
let T = 0; 
const userRequests = new Map(); 

function run() {
    const lines = document.getElementById("input").value.trim().split("\n");
    let output = [];

    for (let line of lines) {
        const parts = line.trim().split(" ");
        const cmd = parts[0];

        if (cmd === "INIT") {
            K = Number(parts[1]);
            T = Number(parts[2]);
            userRequests.clear();

        } else if (cmd === "REQUEST") {
            const userId = parts[1];
            const time = Number(parts[2]);

            if (!userRequests.has(userId)) {
                userRequests.set(userId, []);
            }

            const queue = userRequests.get(userId);

            while (queue.length > 0 && queue[0] <= time - T) {
                queue.shift();
            }

            if (queue.length < K) {
                queue.push(time);
                output.push("ALLOWED");
            } else {
                output.push("BLOCKED");
            }

        } else if (cmd === "STATUS") {
            const userId = parts[1];
            const count = userRequests.get(userId)?.length || 0;
            output.push(count.toString());
        }
    }

    document.getElementById("output").innerText = output.join("\n");
}
