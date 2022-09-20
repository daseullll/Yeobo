import React from 'react';
import Destination from './Destination';
import './Boddari.scss';
import luggage from './images/luggage.png';

function Boddari() {
  return (
    <div className="boddariRoot">
      <div className="header">
        <button>이전</button>
        <p>보따리 만들기</p>
        <button>다음</button>
      </div>

      <div className="recoList">
        <div>
          <Destination />
        </div>
        <div>
          <Destination />
        </div>
        <div>
          <Destination />
        </div>
      </div>

      <div className="boddari">
        <div className="image">
          <img src={luggage} alt="boddariImg" />  
        </div>
      </div>
    </div>
  );
}
export default Boddari;