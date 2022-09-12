export default {
  setComments(state, comments){
    state.comments = comments;
  },
  deleteComment(state, comment){
    state.comments = state.comments.filter(item => item.id != comment.id);
  },
	addComment(state, comment) {
		state.comments.push(comment);
	}
}