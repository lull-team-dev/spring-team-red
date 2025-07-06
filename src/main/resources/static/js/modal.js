// -----------------------------
// キャンセルボタンやモーダルの要素を取得
// -----------------------------
const buttons = document.querySelectorAll(".cancel-button"); // 一覧の「キャンセル可能」ボタン
const modal = document.getElementById("cancelModal"); // 確認用モーダル
const reservationIdInput = document.getElementById("reservationId"); // 非表示の予約ID

// モーダル内に表示する予約情報の各要素
const modalHotelName = document.getElementById("modal-hotel-name");
const modalPlanName = document.getElementById("modal-plan-name");
const modalCheckIn = document.getElementById("modal-check-in");
const modalCheckOut = document.getElementById("modal-check-out");

// -----------------------------
// 「キャンセル可能」ボタンが押されたときの処理
// モーダルを開いて、予約情報をセットする
// -----------------------------

// フェードイン表示(クラス追加)
function showModal(modalElement) {
  modalElement.classList.add("show");
}

// フェードアウト非表示(クラス削除)
function hideModal(modalElement) {
  modalElement.classList.remove("show");
}

buttons.forEach((button) => {
  button.addEventListener("click", () => {
    // ボタンのdata属性から情報を取得してモーダルにセット
    reservationIdInput.value = button.getAttribute("data-id");
    modalHotelName.textContent = button.getAttribute("data-hotel-name");
    modalPlanName.textContent = button.getAttribute("data-plan-name");
    modalCheckIn.textContent = button.getAttribute("data-check-in");
    modalCheckOut.textContent = button.getAttribute("data-check-out");

    // モーダルを表示
    showModal(modal);
  });
});

// -----------------------------
// モーダルの「戻る」ボタンで閉じる処理
// -----------------------------
function closeModal() {
  hideModal(modal);
}

// -----------------------------
// 「キャンセルする」ボタンが押されたときの処理
// fetchでサーバーへキャンセルを送信し、結果によって表示を切り替える
// -----------------------------
document
  .getElementById("confirmCancelBtn")
  .addEventListener("click", async () => {
    const reservationId = document.getElementById("reservationId").value;
    try {
      // fetchでJSONをPOST送信
      // awaitは結果が返ってくるまで処理を待つやつ
      // fetchはサーバーにHTTPリクエスト送る
      const response = await fetch("/bookingList/cancel", {
        method: "post", // postでの送信
        headers: { "Content-type": "application/json" }, //JSON形式で送信
        body: JSON.stringify({ reservationId: reservationId }), //JSオブジェクトをJSONに変換（ここではreservationIdをJSONに変換）
      });

      // サーバーからのレスポンスを取得
      const result = await response.json();

      // -----------------------------
      // キャンセル成功時の処理
      // 確認モーダルを閉じて、完了モーダルを開く
      // -----------------------------
      if (result.success) {
        hideModal(modal);

        // 完了モーダルに予約内容をセット
        document.getElementById("success-hotel-name").textContent =
          modalHotelName.textContent;
        document.getElementById("success-plan-name").textContent =
          modalPlanName.textContent;
        document.getElementById("success-check-in").textContent =
          modalCheckIn.textContent;
        document.getElementById("success-check-out").textContent =
          modalCheckOut.textContent;

        // 完了モーダルを表示
        showModal(document.getElementById("successModal"));
      }
      // -----------------------------
      // キャンセル失敗時の処理
      // -----------------------------
      else {
        alert("キャンセル失敗: " + result.message);
      }
    } catch (error) {
      alert("通信エラーが発生しました");
      console.error(error);
    }
  });
