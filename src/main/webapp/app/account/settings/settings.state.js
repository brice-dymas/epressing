(function() {
    'use strict';

    angular
        .module('epressingApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider ) {
        $stateProvider
        .state('settings', {
            parent: 'account',
            url: '/profile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.settings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/settings/userProfile.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('utilisateur');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('settings');
                    return $translate.refresh();
                }]
            }
        })
        .state('userProfileEdit', {
            parent: 'settings',
            url: '/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal','$localStorage', function($stateParams, $state, $uibModal, $localStorage) {
                $uibModal.open({
                    templateUrl: 'app/account/settings/userProfile-edit.html',
                    controller: 'UserProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['User', function(User) {
                            /*return Utilisateur.get({id : $stateParams.id}).$promise;*/
                            return $localStorage.userConnected.$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: 'settings' });
                }, function() {
                    $state.go('settings');
                });
            }]
        })
        /*.state('userProfileEdit', {
            parent: 'account',
            url: '/profile/edit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.settings'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/account/settings/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        return $translate.refresh();
                    }]
                }
                }).result.then(function() {
                    $state.go('settings', null, { reload: 'settings' });
                }, function() {
                    $state.go('settings');
                });
            }]
        });    */
        /*.state('userProfileEdit', {
            parent: 'account',
            url: '/profile/edit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.settings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/settings/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('settings');
                    return $translate.refresh();
                }]
            }
        });*/
        /*$stateProvider.state('settings', {
            parent: 'account',
            url: '/settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.settings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/settings/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('settings');
                    return $translate.refresh();
                }]
            }
        });*/
    }
})();
