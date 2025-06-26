function resultChange(type) {
    const modalResult = document.getElementById("modalResult");
    modalResult.value = type;
    modalResult.dispatchEvent(new Event("change"));
}

function backgroundCancel(event, modalId, type) {
    if (event.target.classList.contains("simpleModalOverlay")) {
        hideModal(modalId);
        if (type === 'prompt') {
            const modalResult = document.getElementById("modalResult");
            modalResult.value = 'cancel';
            modalResult.dispatchEvent(new Event("change"));
        }
    }
}

function hideModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}
