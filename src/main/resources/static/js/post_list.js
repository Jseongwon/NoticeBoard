(async () => {
    // const page_elements = document.getElementsByClassName("page-link");
    // Array.from(page_elements).forEach(function(element) {
    //     element.addEventListener('click', function() {
    //         document.getElementById('page').value = this.dataset.page;
    //         document.getElementById('searchForm').submit();
    //     });
    // });
    // const searchButtonElement = document.getElementById("searchButton");
    // searchButtonElement.addEventListener('click', function() {
    //     document.getElementById('keyword').value = document.getElementById('search_keyword').value;
    //     document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
    //     document.getElementById('searchForm').submit();
    // });

    const paginationElement = document.getElementById("pagination");
    const searchButtonElement = document.getElementById("searchButton");

    const searchFormElement = document.getElementById('searchForm');

    paginationElement.addEventListener('click', onPaginationItemClicked);
    searchButtonElement.addEventListener('click', onSearchButtonClicked);

    function onPaginationItemClicked(event) {
        if(event.target.classList.contains("page-link")) {
            document.getElementById('page').value = event.target.dataset.page;
            searchFormElement.submit();
        }
    }

    function onSearchButtonClicked(event) {
        document.getElementById('keyword').value = document.getElementById('search_keyword').value;
        document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
        document.getElementById('searchForm').submit();
    }
})();