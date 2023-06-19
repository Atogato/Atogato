'use client'
import { useState } from 'react';
export default function Filter(){
  const [isActive1, setIsActive1] = useState(false);
  const [isActive2, setIsActive2] = useState(false);
  const [isActive3, setIsActive3] = useState(false);

  const handleToggle1 = () => {
    setIsActive1(!isActive1);
  };

  const handleToggle2 = () => {
    setIsActive2(!isActive2);
  };

  const handleToggle3 = () => {
    setIsActive3(!isActive3);
  };

  return (
    <div className='flex'>
      <div className="relative mr-3">
        <button
          onClick={handleToggle1}
          className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md focus:outline-none"
        >
          지역
        </button>
        {isActive1 && (
          <div className="absolute mt-2 w-20 bg-white shadow-lg rounded-md text-center">
            <ul className="py-2">
              <li className="px-2 py-2 hover:bg-gray-100">서울</li>
              <li className="px-2 py-2 hover:bg-gray-100">경기</li>
              <li className="px-2 py-2 hover:bg-gray-100">부산</li>
            </ul>
          </div>
        )}
      </div>
      <div className="relative mr-3">
        <button
          onClick={handleToggle2}
          className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md focus:outline-none"
        >
          장르
        </button>
        {isActive2 && (
          <div className="absolute mt-2 w-20 bg-white shadow-lg rounded-md text-center">
            <ul className="py-2">
              <li className="px-2 py-2 hover:bg-gray-100">Option 1</li>
              <li className="px-2 py-2 hover:bg-gray-100">Option 2</li>
              <li className="px-2 py-2 hover:bg-gray-100">Option 3</li>
            </ul>
          </div>
        )}
      </div>
      <div className="relative mr-3">
        <button
          onClick={handleToggle3}
          className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md focus:outline-none"
        >
          Menu
        </button>
        {isActive3 && (
          <div className="absolute mt-2 w-20 bg-white shadow-lg rounded-md text-center">
            <ul className="py-2">
              <li className="px-2 py-2 hover:bg-gray-100">Option 1</li>
              <li className="px-2 py-2 hover:bg-gray-100">Option 2</li>
              <li className="px-2 py-2 hover:bg-gray-100">Option 3</li>
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};
