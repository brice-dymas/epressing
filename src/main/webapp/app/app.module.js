(function() {
    'use strict';

    angular
        .module('epressingApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler','$localStorage', '$rootScope'];

    function run(stateHandler, translationHandler,$localStorage, $rootScope) {
        stateHandler.initialize();
        translationHandler.initialize(); 

        $rootScope.commandeForm = $localStorage.commandeForm;
        $rootScope.userConnected = {};
        if ($rootScope.commandeForm == null) {
            $rootScope.commandeForm = {
                'commande': null,
                'ligneCommandes': []
            };
        }
    }
})();
