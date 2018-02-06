(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientDialogController', PacientDialogController);

    PacientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Pacient', 'UserBD', 'Company', 'Gender', 'CivilStatus', 'Religion', 'EthnicGroup', 'AcademicDegree', 'SocioeconomicLevel', 'Occupation', 'LivingPlace', 'PrivateHealthInsurance', 'SocialHealthInsurance'];

    function PacientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Pacient, UserBD, Company, Gender, CivilStatus, Religion, EthnicGroup, AcademicDegree, SocioeconomicLevel, Occupation, LivingPlace, PrivateHealthInsurance, SocialHealthInsurance) {
        var vm = this;

        vm.pacient = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userbds = UserBD.query({filter: 'pacient-is-null'});
        $q.all([vm.pacient.$promise, vm.userbds.$promise]).then(function() {
            if (!vm.pacient.userBD || !vm.pacient.userBD.id) {
                return $q.reject();
            }
            return UserBD.get({id : vm.pacient.userBD.id}).$promise;
        }).then(function(userBD) {
            vm.userbds.push(userBD);
        });
        vm.companies = Company.query();
        vm.genders = Gender.query();
        vm.civilstatuses = CivilStatus.query();
        vm.religions = Religion.query();
        vm.ethnicgroups = EthnicGroup.query();
        vm.academicdegrees = AcademicDegree.query();
        vm.socioeconomiclevels = SocioeconomicLevel.query();
        vm.occupations = Occupation.query();
        vm.livingplaces = LivingPlace.query();
        vm.privatehealthinsurances = PrivateHealthInsurance.query();
        vm.socialhealthinsurances = SocialHealthInsurance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pacient.id !== null) {
                Pacient.update(vm.pacient, onSaveSuccess, onSaveError);
            } else {
                Pacient.save(vm.pacient, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:pacientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateofbirth = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
