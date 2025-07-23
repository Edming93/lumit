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
        if (type == 'reset') {
            return await FETCH.patch(`/api/admin/user/info/${id}`, {})
        }
        const value = element.previousElementSibling.value

        if (type == 'role') {
            return await FETCH.patch(`/api/admin/user/info/${id}`, {roleId: value})
        } else if (type == 'name') {
            return await FETCH.patch(`/api/admin/info/${id}`, {name: value})
        }
    })
    if (!result) {
        showToast("오류 발생", 3000);
        return false;
    }
    if (type === "name" || type === "role") {
        element.setAttribute("disabled", true);
    }
    if (type == "reset") {
        location.reload()
    }
    showToast("수정 완료");
    hideModal("update")
    // location.reload();
}

async function deleteInfo(id) {
    let result = await showModal("delete").then(async function (result) {
        if (!result) {
            return false;
        }
        return await FETCH.delete(`/api/admin/user/info/${id}`);
    });
    if (!result) {
        return false;
    }
    location.reload();
}

async function showModal(type) {
    const result = document.getElementById("modalResult");
    return new Promise(function (resolve) {
        const listener = function () {
            if (result.value === 'action') {
                hideModal(type);
                resolve(true);
            } else if (result.value === 'cancel') {
                hideModal(type);
                resolve(false);
            }
            result.removeEventListener("change", listener);
        };
        result.addEventListener("change", listener);

        // ✅ 모달 열기
        const modalId = type === "update" ? "updateModal" : "deleteModal";
        document.getElementById(modalId).style.display = "flex";
    });
}

function hideModal(type) {
    if (type === 'update') {
        document.getElementById("updateModal").style.display = 'none';
    } else if (type === 'delete') {
        document.getElementById("deleteModal").style.display = 'none';
    }
}

