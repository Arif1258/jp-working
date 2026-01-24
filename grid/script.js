let grid = [];
let n = 0;

function run() {
    const lines = document.getElementById("input").value.trim().split("\n");
    let output = [];

    for (let line of lines) {
        const parts = line.trim().split(" ");
        const cmd = parts[0];

        if (cmd === "INIT") {
            n = Number(parts[1]);
            grid = Array.from({ length: n }, () => Array(n).fill("."));

        } else if (cmd === "PAINT") {
            const x = Number(parts[1]);
            const y = Number(parts[2]);
            const color = parts[3];
            if (isValid(x, y)) {
                grid[x][y] = color;
            }

        } else if (cmd === "ERASE") {
            const x = Number(parts[1]);
            const y = Number(parts[2]);
            if (isValid(x, y)) {
                grid[x][y] = ".";
            }

        } else if (cmd === "FILL") {
            const x = Number(parts[1]);
            const y = Number(parts[2]);
            const color = parts[3];
            if (isValid(x, y)) {
                floodFill(x, y, grid[x][y], color);
            }

        } else if (cmd === "PRINT") {
            for (let i = 0; i < n; i++) {
                output.push(grid[i].join(" "));
            }
        }
    }

    document.getElementById("output").innerText = output.join("\n");
}

function isValid(x, y) {
    return x >= 0 && y >= 0 && x < n && y < n;
}

function floodFill(x, y, oldColor, newColor) {
    if (!isValid(x, y)) return;
    if (grid[x][y] !== oldColor || oldColor === newColor) return;

    grid[x][y] = newColor;

    floodFill(x + 1, y, oldColor, newColor);
    floodFill(x - 1, y, oldColor, newColor);
    floodFill(x, y + 1, oldColor, newColor);
    floodFill(x, y - 1, oldColor, newColor);
}
