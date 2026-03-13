/**
 * 숫자 롤링 애니메이션 (number-flow 스타일)
 *
 * 각 자릿수가 독립적으로 위/아래로 슬라이드되는 슬롯머신 효과.
 *
 * 사용법 - HTML data 속성:
 *   <h2 data-animate-value="16777216" data-animate-suffix="원">0원</h2>
 *   <span data-animate-value="12.5" data-animate-prefix="+" data-animate-suffix="%" data-animate-decimal="1">0%</span>
 *
 * 사용법 - JS 직접 호출:
 *   import { animateValue } from './components/animateValue.js';
 *   animateValue(element, { to: 16777216, suffix: '원' });
 *
 * 옵션 (data-animate-*):
 *   value    : 목표 숫자 (필수)
 *   prefix   : 접두사 (예: '+', '₩')
 *   suffix   : 접미사 (예: '원', '건', '%')
 *   duration : 애니메이션 시간 ms (기본: 1000)
 *   decimal  : 소수점 자릿수 (기본: 0)
 *   comma    : 천 단위 콤마 (기본: "true")
 */

const DIGITS = "0123456789";

/**
 * 포맷된 문자열 생성
 */
function formatNumber(value, decimal, useComma) {
  const fixed = Math.abs(value).toFixed(decimal);
  if (!useComma) return fixed;
  const [intPart, decPart] = fixed.split(".");
  const formatted = intPart.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  return decPart !== undefined ? formatted + "." + decPart : formatted;
}

/**
 * 단일 요소에 슬롯머신 스타일 롤링 애니메이션 적용
 */
export function animateValue(el, options = {}) {
  const to = options.to ?? parseFloat(el.dataset.animateValue);
  const prefix = options.prefix ?? (el.dataset.animatePrefix || "");
  const suffix = options.suffix ?? (el.dataset.animateSuffix || "");
  const duration = options.duration ?? (parseInt(el.dataset.animateDuration) || 1000);
  const decimal = options.decimal ?? (parseInt(el.dataset.animateDecimal) || 0);
  const useComma = options.comma ?? el.dataset.animateComma !== "false";

  const formatted = formatNumber(to, decimal, useComma);
  const fullText = prefix + formatted + suffix;

  // 컨테이너 설정
  el.textContent = "";
  el.style.display = "inline-flex";
  el.style.overflow = "hidden";
  el.style.verticalAlign = "baseline";

  // 각 문자에 대해 슬롯 생성
  const chars = fullText.split("");
  const slots = [];

  chars.forEach((char, i) => {
    const isDigit = DIGITS.includes(char);
    const wrapper = document.createElement("span");
    wrapper.style.display = "inline-block";
    wrapper.style.overflow = "hidden";
    wrapper.style.height = "1em";
    wrapper.style.lineHeight = "1em";
    wrapper.style.verticalAlign = "top";

    if (isDigit) {
      const digitVal = parseInt(char);
      const reel = document.createElement("span");
      reel.style.display = "block";
      reel.style.transition = "none";

      // 릴: 0-9 + 0-target (위에서 아래로 슬라이드)
      const reelDigits = [];
      for (let d = 0; d <= digitVal; d++) {
        reelDigits.push(d);
      }

      reel.innerHTML = reelDigits.map((d) => `<span style="display:block;height:1em;line-height:1em;">${d}</span>`).join("");

      // 시작 위치: 0이 보이도록
      reel.style.transform = "translateY(0)";

      wrapper.appendChild(reel);
      slots.push({ wrapper, reel, targetIndex: reelDigits.length - 1, charIndex: i });
    } else {
      // 비숫자(콤마, 점, 접두사, 접미사)
      wrapper.textContent = char;
      wrapper.style.transform = "translateY(0.3em)";
      wrapper.style.opacity = "0";
    }

    el.appendChild(wrapper);
  });

  // 다음 프레임에서 애니메이션 시작
  requestAnimationFrame(() => {
    requestAnimationFrame(() => {
      // 비숫자 요소 페이드인
      el.querySelectorAll("span").forEach((span) => {
        if (span.children.length === 0 && span.parentElement === el) {
          span.style.transition = `opacity ${duration * 0.4}ms ease, transform ${duration * 0.4}ms ease`;
          span.style.transform = "translateY(0)";
          span.style.opacity = "1";
        }
      });

      // 슬롯 롤링
      slots.forEach(({ reel, targetIndex, charIndex }) => {
        // 각 자릿수마다 약간의 딜레이 (왼→오 순서)
        const delay = charIndex * 40;
        const easing = "cubic-bezier(0.16, 1, 0.3, 1)"; // easeOutExpo

        reel.style.transition = `transform ${duration}ms ${easing} ${delay}ms`;
        reel.style.transform = `translateY(-${targetIndex}em)`;
      });
    });
  });
}

/**
 * data-animate-value 속성을 가진 모든 요소를 찾아
 * IntersectionObserver로 뷰포트 진입 시 애니메이션 실행
 */
export function initAnimateValues(root = document) {
  const elements = root.querySelectorAll("[data-animate-value]");
  if (!elements.length) return;

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          animateValue(entry.target);
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.1 },
  );

  elements.forEach((el) => observer.observe(el));
}
