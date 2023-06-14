const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");

const pane = new Tweakpane.Pane();
//
const PARAMS = {
    center_x: 640 / 4,
    center_y: 480 / 2,
    center_spread: 10
};

pane.addInput(PARAMS, 'center_x', {min: 0, max: canvas.width, step: 1});
pane.addInput(PARAMS, 'center_y', {min: 0, max: canvas.height, step: 1});
pane.addInput(PARAMS, 'center_spread', {min: 0, max: 100, step: 1});

pane.on('change', (ev) => {
    drawPolygons();
});

class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
}

// To find orientation of ordered triplet (p, q, r).
// The function returns following values
// 0 --> p, q and r are collinear
// 1 --> Clockwise
// 2 --> Counterclockwise
function orientation(p, q, r) {
    let val = (q.y - p.y) * (r.x - q.x) -
        (q.x - p.x) * (r.y - q.y);

    if (val === 0) return 0;  // collinear
    return (val > 0) ? 1 : 2; // clock or counterclock wise
}

// Prints convex hull of a set of n points.
function convexHull(points, n) {
    // There must be at least 3 points
    if (n < 3) return;

    // Initialize Result
    let hull = [];

    // Find the leftmost point
    let l = 0;
    for (let i = 1; i < n; i++)
        if (points[i].x < points[l].x)
            l = i;

    // Start from leftmost point, keep moving
    // counterclockwise until reach the start point
    // again. This loop runs O(h) times where h is
    // number of points in result or output.
    let p = l, q;
    do {

        // Add current point to result
        hull.push(points[p]);

        // Search for a point 'q' such that
        // orientation(p, q, x) is counterclockwise
        // for all points 'x'. The idea is to keep
        // track of last visited most counterclock-
        // wise point in q. If any point 'i' is more
        // counterclock-wise than q, then update q.
        q = (p + 1) % n;

        for (let i = 0; i < n; i++) {
            // If i is more counterclockwise than
            // current q, then update q
            if (orientation(points[p], points[i], points[q]) === 2)
                q = i;
        }

        // Now q is the most counterclockwise with
        // respect to p. Set p as q for next iteration,
        // so that q is added to result 'hull'
        p = q;

    } while (p !== l);  // While we don't come to first
    // point

    // return result
    return hull.values();
}

const getVertexNumber = () => {
    return getRandomNumberInRange(3, 6);
}

function getSign() {
    return Math.round(Math.random()) * 2 - 1;
}

function getRandomNumberInRange(min, max) {
    return Math.floor(Math.random() * max) + min;
}

function getPolygonCenter() {
    return {
        x: PARAMS.center_x + getSign() * getRandomNumberInRange(5, 10 * PARAMS.center_spread),
        y: PARAMS.center_y + getSign() * getRandomNumberInRange(5, 10 * PARAMS.center_spread),
    };
}

function drawPolygons() {

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    //  choose how many polygons we should have
    const polygons_count = 10;

    // choose how many vertexes we should have
    let vertex_num = getVertexNumber();


    for (let pc = 0; pc < polygons_count; pc++) {
        let polygon_center = getPolygonCenter();
        let vertices = [];
        for (let i = 0; i < vertex_num; i++) {
            let point = new Point(
                polygon_center.x + Math.floor(Math.random() * 30) + 25,
                polygon_center.y + Math.floor(Math.random() * 30) + 25
            );
            vertices.push(point);
        }

        let convexVertices = convexHull(vertices, vertices.length);

        ctx.beginPath();
        ctx.strokeStyle = "#FF0000";
        let start = false;
        let startVertex = new Point(0, 0);
        for (let v of convexVertices) {
            if (!start) {
                ctx.moveTo(v.x + 5 / 2, v.y + 5 / 2);
                startVertex.x = v.x
                startVertex.y = v.y;
                start = true;
                continue;
            }
            ctx.lineTo(v.x + 5 / 2, v.y + 5 / 2);
        }
        ctx.lineTo(startVertex.x + 5 / 2, startVertex.y + 5 / 2);
        ctx.stroke();
    }
}

drawPolygons();