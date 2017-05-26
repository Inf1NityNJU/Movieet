import React from 'react';
import Header from './Header';
import Footer from './Footer';

function MainLayout({ children, location }) {
  return (
    <div>
      <Header location={location}/>
      <div className="content">
        <div className="main">
          {children}
        </div>
      </div>
      <Footer/>
    </div>
  );
}

export default MainLayout;
