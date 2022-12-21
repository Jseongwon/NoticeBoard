(async () => {
    // const deleteElements = document.getElementsByClassName("delete");
    // Array.from(deleteElements).forEach(function(element) {
    //     element.addEventListener('click', function() {
    //         if(confirm("정말로 삭제하시겠습니까?")) {
    //             location.href = this.dataset.uri;
    //         };
    //     });
    // });
    // const recommend_elements = document.getElementsByClassName("recommend");
    // Array.from(recommend_elements).forEach(function(element) {
    //     element.addEventListener('click', function() {
    //         if(confirm("정말로 추천하시겠습니까?")) {
    //             location.href = this.dataset.uri;
    //         };
    //     });
    // });
    const contentElement = document.getElementById('content-layout');

    contentElement.addEventListener('click', (event) => {
        if(event.target.classList.contains("delete")) {
            onDeleteButtonClicked(event);
        }

        if(event.target.classList.contains("recommend")) {
            onRecommendButtonClicked(event);
        }
    });

    function onDeleteButtonClicked(event) {
        if(confirm("정말로 삭제하시겠습니까?")) {
            location.href = event.target.dataset.uri;
        }
    }

    function onRecommendButtonClicked(event) {
        if(confirm("정말로 추천하시겠습니까?")) {
            location.href = event.target.dataset.uri;
        }
    }
})();