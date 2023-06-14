const calculateArea = () => {
    const height = document.getElementById("height").value;
    const base = document.getElementById("base").value;
    
    let area = 1/2 * base * height;

    document.getElementById("result").innerHTML = area;
};