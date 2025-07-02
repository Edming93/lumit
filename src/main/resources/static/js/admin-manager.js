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
            return await FETCH.patch(`/api/admin/info/${id}`, {})
        }
        const value = element.previousElementSibling.value

        if (type == 'role') {
            return await FETCH.patch(`/api/admin/info/${id}`, {roleId: value})
        } else if (type == 'name') {
            return await FETCH.patch(`/api/admin/info/${id}`, {name: value})
        }
    })
    if (!result) {
        showToast("오류 발생", 3000);
        return false;
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
        return await FETCH.delete(`/api/admin/info/${id}`);
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

function showToast(message, duration = 2000) {
    const toast = document.getElementById("toast");
    toast.textContent = message;
    toast.classList.remove("hidden");
    toast.classList.add("show");
    console.log("토스트 실행됨")
    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => {
            toast.classList.add("hidden");
        }, 300); // fade-out 애니메이션 시간과 맞춰줌
    }, duration);
}
