(function() {
    'use strict';

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carte-bancaire', {
            parent: 'entity',
            url: '/carte-bancaire?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.carteBancaire.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaires.html',
                    controller: 'CarteBancaireController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carteBancaire');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('carte-bancaire-detail', {
            parent: 'carte-bancaire',
            url: '/carte-bancaire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.carteBancaire.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaire-detail.html',
                    controller: 'CarteBancaireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carteBancaire');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarteBancaire', function($stateParams, CarteBancaire) {
                    return CarteBancaire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carte-bancaire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carte-bancaire-detail.edit', {
            parent: 'carte-bancaire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaire-dialog.html',
                    controller: 'CarteBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarteBancaire', function(CarteBancaire) {
                            return CarteBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carte-bancaire.new', {
            parent: 'carte-bancaire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaire-dialog.html',
                    controller: 'CarteBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                codeHVC: null,
                                dateExpiration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carte-bancaire', null, { reload: 'carte-bancaire' });
                }, function() {
                    $state.go('carte-bancaire');
                });
            }]
        })
        .state('carte-bancaire.edit', {
            parent: 'carte-bancaire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaire-dialog.html',
                    controller: 'CarteBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarteBancaire', function(CarteBancaire) {
                            return CarteBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carte-bancaire', null, { reload: 'carte-bancaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carte-bancaire.delete', {
            parent: 'carte-bancaire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carte-bancaire/carte-bancaire-delete-dialog.html',
                    controller: 'CarteBancaireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarteBancaire', function(CarteBancaire) {
                            return CarteBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carte-bancaire', null, { reload: 'carte-bancaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
