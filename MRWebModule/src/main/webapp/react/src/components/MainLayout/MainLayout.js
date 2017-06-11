import React from 'react';
import Header from './Header';
import Footer from './Footer';

function MainLayout({children, location, theme = 'light'}) {
    return (
        <div>
            <Header location={location}/>
            <div className="content">
                <div className="main">
                    {children}
                </div>
            </div>
            <Footer theme={theme}/>
        </div>
    );
}

export default MainLayout;
