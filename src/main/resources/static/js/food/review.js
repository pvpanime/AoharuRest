function reviewEl(id, userid, comment, rating, added) {
  const item = document.createElement('div');
  item.dataset.foodReviewId = id;
  item.classList.add('review', 'list-group-item');
  item.innerHTML = `
      <div class="row justify-content-between">
        <div class="col-auto">
          <span class="userid fw-bold text-info-emphasis"></span>      
          <span class="text-body-secondary">
             <i class="bi bi-star-fill" style="color: #ffc614"></i>
             <span class="rating"></span>
          </span>
        </div>
        <span class="added text-body-secondary fw-light col-auto"></span>
      </div>
      <p class="comment-content"></p>
      `
  item.querySelector('.userid').innerText = userid;
  item.querySelector('.rating').innerText = rating;
  item.querySelector('.comment-content').innerText = comment;
  item.querySelector('.added').innerText = added;
  return item;
}

function checkInt(i, name) {
  if (!Number.isInteger(Number(i))) throw Error(`${i} is invalid for ${name}.`)
}

let currentPage = 1

async function fetchReviews(reviews, paginator, foodId, page = currentPage, size = 10) {
  checkInt(foodId, "food id");
  checkInt(page, "page");
  checkInt(size, "size");
  let url = '/api/food/reviews/' + foodId;
  let queryFields = []
  if (!!page) queryFields.push('page=' + page)
  if (!!size) queryFields.push('size=' + size)
  const query = queryFields.join('&')
  if (query) url += '?' + query

  try {
    const { data } = await axios.get(url);

    reviews.innerHTML = ''
    for (const re of data.dtoList ?? []) {
      reviews.append(reviewEl(re.id, re.userid, re.comment, re.rating, re.added));
    }

    paginator.innerHTML = ''
    if (data.total > 0) {
      paginator.append(
        Paginator(page, data.start, data.end, data.last, {
          handler(i) {
            fetchReviews(reviews, paginator, foodId, i, size)
          }
        })
      )
    }

    currentPage = page
  } catch (e) {
    console.error(e)
    console.error("There's an error during fetching reviews.")
  }
}

function findReviewEl(target) {
  while (target != null) {
    if (target.classList.contains("review")) return target
    target = target.parentElement
  }
}

async function registerReview(foodId, comment, rating, userid) {
  try {
    return await axios.post("/api/food/review", {
      foodId, comment, rating, userid
    });
  } catch (error) {
    console.error(error);
    console.error("There's an error during registering reviews.")
  }
}

async function editReview(reviewId, comment, rating) {
  try {
    return await axios.put("/api/food/review", {
      id: reviewId, comment, rating
    })
  } catch (error) {
    console.error(error);
    console.error("There's an error during updating reviews.")
  }
}

