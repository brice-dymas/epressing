(function() {
    'use strict';

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('cart-list', {
            parent: 'commande',
            url: '/cart-view', 
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.cart.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commande/cart-list.html',
                    controller: 'CartController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                 translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commande');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Commande','$rootScope', function($stateParams, Commande, $rootScope) {
                    return $rootScope.maCommande;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'commande',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }
})();
