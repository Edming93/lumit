function backgroundCancel(event, id, type) {
    event.stopPropagation();
    if (event.target == document.getElementById(id)) {
        hideModal(id)
        if (type == "simple") {
            return;
        }
        resultChange("cancel")
    }
}

function hideModal(id) {
    document.getElementById(id).style.display = "none"
}

function resultChange(value) {
    const result = document.getElementById("modalResult")
    result.value = value
    result.dispatchEvent(new Event("change"))
}