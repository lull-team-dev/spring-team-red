document.addEventListener("DOMContentLoaded", function () {

			const hamburger = document.getElementById('hamburger');
			const sidebar = document.getElementById('sidebar');
			const overlay = document.getElementById('overlay');
			
            if (!hamburger || !sidebar || !overlay) return;

			hamburger.addEventListener('click', () => {
				hamburger.classList.toggle('active');
				sidebar.classList.toggle('active');
				overlay.classList.toggle('active');
			});
			
			overlay.addEventListener('click', () => {
				hamburger.classList.remove('active');
				sidebar.classList.remove('active');
				overlay.classList.remove('active');
			});
		});