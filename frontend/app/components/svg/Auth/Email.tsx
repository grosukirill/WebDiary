import React from "react";

function Icon() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      width="19"
      height="15"
      fill="none"
      viewBox="0 0 19 15"
    >
      <path fill="url(#pattern2)" d="M0 0H19V15H0z"></path>
      <defs>
        <pattern
          id="pattern2"
          width="1"
          height="1"
          patternContentUnits="objectBoundingBox"
        >
          <use transform="scale(.05263 .06667)" xlinkHref="#image2"></use>
        </pattern>
        <image
          id="image2"
          width="19"
          height="15"
          xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAAPCAYAAAAGRPQsAAABE0lEQVQ4jaXULUiDURTG8d8+mkWYzW6zGMfCikajM4gyLAODYFpdMm5FsNgETYLBIGaTYrGZtFhEWDIYNrlwJjo297o95d57znP+7+G83JtrtVo1HGPe9OqiUcRRgB7wOAVuGSuJk2ClCC6hiZt/gFZxEftSPja36OEKtYygzfD3ot4Ado8K3nCO/QmglD8LfyXqv2FiXmU8oYND5IYguYh3wlf+Oef8kPklvnQX8ztBIXKFODcjXwm/cTDRehXXqOMSC7HWI14N3y8Vx8zkA+vRyRaeMYdT7OJzVNGozgZKBdtoB6gd55GgSbCkPg6wGGv/L/Mk2ECvWUxZYZmUfsA7Nma86GuJk2B78WrszADrovEFHao5g4iQ6NUAAAAASUVORK5CYII="
        ></image>
      </defs>
    </svg>
  );
}

export default Icon;
