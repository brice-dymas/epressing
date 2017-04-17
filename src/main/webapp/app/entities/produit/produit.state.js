(function() {
    'use strict'; 

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider,$rootScope) {
        $stateProvider
        .state('produit', {
            parent: 'entity',
            url: '/produit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.produit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produit/produits.html',
                    controller: 'ProduitController',
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
                    $translatePartialLoader.addPart('produit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('produit-detail', {
            parent: 'produit',
            url: '/produit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'epressingApp.produit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produit/produit-detail.html',
                    controller: 'ProduitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Produit', function($stateParams, Produit) {
                    return Produit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'produit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('produit-detail.edit', {
            parent: 'produit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit/produit-dialog.html',
                    controller: 'ProduitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produit', function(Produit) {
                            return Produit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produit.new', {
            parent: 'produit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit/produit-dialog.html',
                    controller: 'ProduitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                photo: null,
                                photoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produit', null, { reload: 'produit' });
                }, function() {
                    $state.go('produit');
                });
            }]
        })
        .state('produit.addToCart', {
            parent: 'produit',
            url: '/addToCart/{id}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal','$rootScope', function($stateParams, $state, $uibModal,$rootScope) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit/produit-addToCart.html',
                    controller: 'ProduitAddToCartController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null,
                                quantite: null,
                                etat: null,
                                tarif: 0,
                                produit: {
                                    id:null,
                                    libelle:null,
                                    photo:null
                                },
                                commande: {
                                    id:null,
                                    dateCommande:null,
                                    dateFacture:null,
                                    dateFacturation:null,
                                    dateCueillette:null,
                                    dateLivraison:null,
                                    netAPayer:null,
                                    etat: 'En attente',
                                    adresseCueillette:null,
                                    adresseLivraison:null,
                                    adresseFacturation:null
                                },
                                operation:{ 
                                    id:null,
                                    libelle: null,
                                    description: null
                                },
                                caracteristique: {
                                    id: null,
                                    couleur: null,
                                    marque: null,
                                    libelle: null
                                }
                            };
                        },
                        
                    }
                }).result.then(function() {
                    $state.go('produit', null, { reload: 'produit' });
                }, function() {
                    $state.go('produit');
                });
            }]
        })
        .state('produit.edit', {
            parent: 'produit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit/produit-dialog.html',
                    controller: 'ProduitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Produit', function(Produit) {
                            return Produit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produit', null, { reload: 'produit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produit.delete', {
            parent: 'produit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit/produit-delete-dialog.html',
                    controller: 'ProduitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Produit', function(Produit) {
                            return Produit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produit', null, { reload: 'produit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
