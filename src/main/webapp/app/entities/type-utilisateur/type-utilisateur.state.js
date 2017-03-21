(function() {
    'use strict';

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-utilisateur', {
            parent: 'entity',
            url: '/type-utilisateur?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.typeUtilisateur.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateurs.html',
                    controller: 'TypeUtilisateurController',
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
                    $translatePartialLoader.addPart('typeUtilisateur');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-utilisateur-detail', {
            parent: 'type-utilisateur',
            url: '/type-utilisateur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.typeUtilisateur.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateur-detail.html',
                    controller: 'TypeUtilisateurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('typeUtilisateur');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TypeUtilisateur', function($stateParams, TypeUtilisateur) {
                    return TypeUtilisateur.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-utilisateur',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-utilisateur-detail.edit', {
            parent: 'type-utilisateur-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateur-dialog.html',
                    controller: 'TypeUtilisateurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeUtilisateur', function(TypeUtilisateur) {
                            return TypeUtilisateur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-utilisateur.new', {
            parent: 'type-utilisateur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateur-dialog.html',
                    controller: 'TypeUtilisateurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-utilisateur', null, { reload: 'type-utilisateur' });
                }, function() {
                    $state.go('type-utilisateur');
                });
            }]
        })
        .state('type-utilisateur.edit', {
            parent: 'type-utilisateur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateur-dialog.html',
                    controller: 'TypeUtilisateurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeUtilisateur', function(TypeUtilisateur) {
                            return TypeUtilisateur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-utilisateur', null, { reload: 'type-utilisateur' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-utilisateur.delete', {
            parent: 'type-utilisateur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-utilisateur/type-utilisateur-delete-dialog.html',
                    controller: 'TypeUtilisateurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeUtilisateur', function(TypeUtilisateur) {
                            return TypeUtilisateur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-utilisateur', null, { reload: 'type-utilisateur' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
