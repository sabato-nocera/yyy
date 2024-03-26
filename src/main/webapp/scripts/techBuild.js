const slider = document.querySelector('.slide-container');
const slides = document.querySelectorAll('.slide');
const noProductsMsg = document.querySelector('.no-products-msg');
const prevBtn = document.querySelector('.prev-btn');
const nextBtn = document.querySelector('.next-btn');
let touchStartX = 0;
let touchEndX = 0;

if (slides.length === 0) {
	noProductsMsg.style.display = 'block';
} else {
	let slidePosition = 0;
	const slideWidth = slides[0].offsetWidth;

	function moveToSlide(position) {
		slider.style.transform = `translateX(-${position * slideWidth}px)`;
		slidePosition = position;
	}

	function moveToNextSlide() {
		if (slidePosition === slides.length - 1) {
			slidePosition = 0;
		} else {
			slidePosition++;
		}
		moveToSlide(slidePosition);
	}

	function moveToPrevSlide() {
		if (slidePosition === 0) {
			slidePosition = slides.length - 1;
		} else {
			slidePosition--;
		}
		moveToSlide(slidePosition);
	}

	function handleTouchStart(e) {
		touchStartX = e.touches[0].clientX;
	}

	function handleTouchMove(e) {
		touchEndX = e.touches[0].clientX;
	}

	function handleTouchEnd() {
		if (touchStartX - touchEndX > 50) {
			moveToNextSlide();
		} else if (touchEndX - touchStartX > 50) {
			moveToPrevSlide();
		}
	}

	prevBtn.addEventListener('click', moveToPrevSlide);
	nextBtn.addEventListener('click', moveToNextSlide);
	slider.addEventListener('touchstart', handleTouchStart, { passive: true });
	slider.addEventListener('touchmove', handleTouchMove, { passive: true });
	slider.addEventListener('touchend', handleTouchEnd);
}