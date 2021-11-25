import React from "react";

function Person() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      width="16"
      height="19"
      fill="none"
      viewBox="0 0 16 19"
    >
      <path fill="url(#pattern1)" d="M0 0H16V19H0z"></path>
      <defs>
        <pattern
          id="pattern1"
          width="1"
          height="1"
          patternContentUnits="objectBoundingBox"
        >
          <use transform="scale(.0625 .05263)" xlinkHref="#image1"></use>
        </pattern>
        <image
          id="image1"
          width="16"
          height="19"
          xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAATCAYAAACZZ43PAAABXUlEQVQ4jZXTvUpcURQF4E+NmJAyFlY2YjeNIb6BkAyWFoKVrdhGBBOL6czPSwSLIMTWn0cQNEUIWKUQERFiHcRgwgnrymW4M04WbC6svdY6+7DPHep0OhqwgDU8T+srPmC3WzrcYN7CF8ziW2o23NZDAa+wjh9o4UWqFW49mp4Bq/ku4rTGn4araxoDZnCGk4arnaQ30y/gFmMN5gpj0fQMOMIE5hrMc+kd9Qv4iDt8RhtDqXa4u2h6BhxjE+PYw8/UXrjNaO7xqCvsNTZq3OOuA0rvd23S+wnKdxvvcJN9T+JpajLcTTTblbcKKKMtZVXl0bzHee3k83CtaJbi+RcwhTe4wDwuGzZQ4TKai3imSsAKRvEWV33MFa6iLZ6V4azoF3YGMFfYiaddAqbxPcSgKNrimS4BI3iSBzMoirZ4Rso7OMw1yn9/PWDCs2xkvwQs4xNe/scUf3CA5b9Mh0tCCYkr8gAAAABJRU5ErkJggg=="
        ></image>
      </defs>
    </svg>
  );
}

export default Person;