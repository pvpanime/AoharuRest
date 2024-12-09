
async function fetchComments(bid, parent, paginator, page = commentPage, size = 50) {
  if (!Number.isInteger(Number(bid))) throw Error(`${bid} is invalid for board id.`)
  let url = '/api/comment/list/' + bid
  let queryFields = []
  if (!!page) queryFields.push('page=' + page)
  if (!!size) queryFields.push('size=' + size)
  const query = queryFields.join('&')
  if (query) url += '?' + query
  try {
    const {data} = await axios.get(url);
    parent.innerHTML = '';
    for (const comment of (data.dtoList ?? [])) {
      const el = document.createElement('li');
      el.classList.add('list-group-item', 'list-group-item-action', 'comment');
      el.dataset.commentId = comment.cid
      el.innerHTML = `
                <span class="fw-bold text-info-emphasis"></span>
                <span class="comment-content"></span>
                <span class="text-muted fw-light"></span>
              `;
      el.querySelector('.fw-bold').innerText = comment.userid;
      el.querySelector('.text-muted').innerText = comment.added;
      el.querySelector('.comment-content').innerText = comment.content;
      parent.append(el);
    }
    paginator.innerHTML = '';
    if (data.total > 0) {
      paginator.append(
        Paginator(page, data.start, data.end, data.last, {
          handler(i) {
            fetchComments(bid, parent, paginator, i, size)
          }
        })
      )
    }
    commentPage = page
  } catch (e) {
    console.error(e)
    console.error("There's an error during fetching board comments.")
  }
}

function submitToDelete(action) {
  const form = document.createElement('form');
  form.method = 'POST';
  form.action = action;
  document.body.appendChild(form);
  form.submit();
}
