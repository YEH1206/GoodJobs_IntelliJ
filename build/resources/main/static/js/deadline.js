function checkDeadline(dtoNo, deadlineInstant) {
    const localNow = new Date();
    const deadlineLocal = new Date(deadlineInstant);
    let recruitStatus = '';

    const year = deadlineLocal.getFullYear();
    let deadlineFormatted;
    if (year > 9000) {
        recruitStatus = "채용중";
        deadlineFormatted = "상시모집";
    } else {
        const month = ('0' + (deadlineLocal.getMonth() + 1)).slice(-2);
        const date = ('0' + deadlineLocal.getDate()).slice(-2);
        deadlineFormatted = `${year}-${month}-${date}`;

        let diff = Math.floor((deadlineLocal - localNow) / (1000 * 60 * 60 * 24));
        if(diff >=0 ) {
            recruitStatus = `D-${diff}`
        }
    }

    // 'yyyy-MM-dd' 형태로 변환된 마감일을 삽입한다.
    //document.getElementById('deadline_' + dtoNo).innerText = deadlineFormatted;

    // 'D-day' 형태로 변환된 마감일을 삽입한다.
    document.getElementById('deadline_' + dtoNo).innerText = deadlineFormatted;

    // 마감 여부를 확인하고 화면에 표시한다.
    const isClosed = document.getElementById('isClosed_' + dtoNo);
    if (deadlineLocal > localNow) {
        isClosed.innerText = recruitStatus;
        isClosed.classList.add("open");
    } else {
        isClosed.innerText = '마감';
        isClosed.classList.add("close");
    }
}