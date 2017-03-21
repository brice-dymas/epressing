(function() {
    'use strict';

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tarif', {
            parent: 'entity',
            url: '/tarif?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.tarif.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarif/tarifs.html',
                    controller: 'TarifController',
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
                    $translatePartialLoader.addPart('tarif');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tarif-detail', {
            parent: 'tarif',
            url: '/tarif/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.tarif.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tarif/tarif-detail.html',
                    controller: 'TarifDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tarif');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tarif', function($stateParams, Tarif) {
                    return Tarif.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tarif',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tarif-detail.edit', {
            parent: 'tarif-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarif/tarif-dialog.html',
                    controller: 'TarifDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarif', function(Tarif) {
                            return Tarif.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarif.new', {
            parent: 'tarif',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarif/tarif-dialog.html',
                    controller: 'TarifDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                montant: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tarif', null, { reload: 'tarif' });
                }, function() {
                    $state.go('tarif');
                });
            }]
        })
        .state('tarif.edit', {
            parent: 'tarif',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarif/tarif-dialog.html',
                    controller: 'TarifDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tarif', function(Tarif) {
                            return Tarif.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarif', null, { reload: 'tarif' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tarif.delete', {
            parent: 'tarif',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tarif/tarif-delete-dialog.html',
                    controller: 'TarifDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tarif', function(Tarif) {
                            return Tarif.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tarif', null, { reload: 'tarif' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
