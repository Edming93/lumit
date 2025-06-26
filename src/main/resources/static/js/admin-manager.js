function roleChange(event, element, value) {
    const btn = element.nextElementSibling;
    if (event.target.value == value) {
        btn.setAttribute("disabled", true);
    } else {
        btn.removeAttribute("disabled");
    }
}

function nameChange(event, element, value) {
    const btn = element.nextElementSibling;
    if (event.target.value == value) {
        btn.setAttribute("disabled", true);
    } else {
        btn.removeAttribute("disabled");
    }
}

async function updateInfo(element, id, type) {
    let result = await showModal("update").then(async function (result) {
        if (!result) {
            return false;
        }
        if (type === 'reset') {
            return await FETCH.patch(`/api/admin/info/${id}`, {});
        }
        const value = element.previousElementSibling.value;
        if (type === 'role') {
            return await FETCH.patch(`/api/admin/info/${id}`, {roleId: value});
        } else if (type === 'name') {
            return await FETCH.patch(`/api/admin/info/${id}`, {name: value});
        }
    });
    if (!result) {
        return false;
    }
    location.reload();
}

async function deleteInfo(id) {
    let result = await showModal("delete").then(async function (result) {
        if (!result) {
            return false;
        }
        return await FETCH.delete(`/api/admin/info/${id}`);
    });
    if (!result) {
        return false;
    }
    location.reload();
}

async function showModal(type) {
    if (type === 'update') {
        document.getElementById("updateModal").style.display = 'flex';
    } else if (type === 'delete') {
        document.getElementById("deleteModal").style.display = 'flex';
    }
    return new Promise(function (resolve) {
        const result = document.getElementById("modalResult");
        result.addEventListener("change", function () {
            if (result.value === 'action') {
                hideModal(type);
                setTimeout(() => resolve(true), 100);
            } else if (result.value === 'cancel') {
                hideModal(type);
                setTimeout(() => resolve(false), 100);
            }
        });
    });
}

function hideModal(type) {
    if (type === 'update') {
        document.getElementById("updateModal").style.display = 'none';
    } else if (type === 'delete') {
        document.getElementById("deleteModal").style.display = 'none';
    }
}
